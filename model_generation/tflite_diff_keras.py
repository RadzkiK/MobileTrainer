# -*- coding: utf-8 -*-
"""
Created on Sat Dec 28 20:12:31 2024

@author: Konrad
"""

import csv
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import sklearn.metrics as sklearn
import seaborn as sns
from sklearn.preprocessing import MinMaxScaler


phone = pd.read_csv("persons.csv")

keras = pd.read_csv("person_keras.csv")

df_merged = pd.merge(keras, phone, on="file_name", how="outer", suffixes=('_keras', '_phone'))

df_merged.set_index('file_name', inplace=True)

df_merged_sorted = df_merged.reindex(columns = sorted(df_merged.columns))

class_to_index = {
    'deadlift_down' or ' deadlift_down': 0,
    'deadlift_up' or ' deadlift_up': 1,
    'pushup_down' or ' pushup_down': 2,
    'pushup_up' or ' pushup_up': 3,
    'rowing_down' or ' rowing_down': 4,
    'rowing_up' or ' rowing_up': 5,
    'squat_down' or ' squat_down': 6,
    'squat_up' or ' squat_up': 7
}

class_labels = [0,1,2,3,4,5,6,7]
class_names = ['deadlift_down', 'deadlift_up', 'pushup_down', 'pushup_up', 'rowing_down', 'rowing_up', 'squat_down', 'squat_up'] 

df_merged_sorted['class_no_keras'] = df_merged_sorted['class_name_keras'].map(class_to_index)

df_merged_sorted['class_no_phone'] = df_merged_sorted['class_name_phone'].map(class_to_index)

dane = df_merged_sorted.dropna(how='any')

cm_keras = sklearn.confusion_matrix(dane["class_no"], dane["class_no_keras"])
cm_keras_dis = sklearn.ConfusionMatrixDisplay(cm_keras, display_labels=class_names)
cm_keras_dis.plot()
plt.xticks(rotation=45)
plt.show()

cm_phone = sklearn.confusion_matrix(dane["class_no"], dane["class_no_phone"])
cm_phone_dis = sklearn.ConfusionMatrixDisplay(cm_phone, display_labels=class_names)
cm_phone_dis.plot()
plt.xticks(rotation=45)
plt.show()

print('Całkowita trafność modelu keras: ', round(sklearn.accuracy_score(dane['class_no'], dane['class_no_keras']), 3))
print('Całkowita trafność modelu tflite na telefonie: ', round(sklearn.accuracy_score(dane['class_no'], dane['class_no_phone']), 3))

cm_keras_multi = sklearn.multilabel_confusion_matrix(dane['class_no'], dane['class_no_keras'])
for i in range(0, len(class_names)):
    display(pd.DataFrame(cm_phone_multi[i], index=['Others', class_names[i]], columns = ['Others', class_names[i]]))
    print('\n')

cm_phone_multi = sklearn.multilabel_confusion_matrix(dane['class_no'], dane['class_no_phone'])
for i in range(0, len(class_names)):
    display(pd.DataFrame(cm_phone_multi[i], index=['Others', class_names[i]], columns = ['Others', class_names[i]]))
    print('\n')
    
def ocen_model_klasyfikacji(y_true, y_pred, positive_class, digits = 2):
    tn, fp, fn, tp = sklearn.multilabel_confusion_matrix(y_true, y_pred)[positive_class].ravel()
    accuracy = (tn+tp)/(tn+fp+fn+tp)
    overall_error_rate = 1 - accuracy
    sensitivity = tp/(fn+tp)
    fnr = fn/(fn+tp)
    specificity = tn/(tn+fp)
    fpr = fp/(tn+fp)
    precision = tp/(fp+tp)
    f1 = (2 * sensitivity * precision) / (sensitivity + precision)
    print('Trafność: ', round(accuracy, digits))
    print('Całkowity współczynnik błędu', round(overall_error_rate, digits))
    print('Czułość: ', round(sensitivity, digits))
    print('Wskaźnik fałszywie negatywnych: ', round(fnr, digits))
    print('Specyficzność: ', round(specificity, digits))
    print('Wskaźnik fałszywie pozytywnych: ', round(fpr, digits))
    print('Precyzja: ', round(precision, digits))
    print('Wynik F1: ', round(f1, digits))
    
print("Ocena modelu keras: \n")
for i in range(0, len(class_names)):
    print('Klasa pozytywna: ', class_names[i], '\n')
    ocen_model_klasyfikacji(dane['class_no'], dane['class_no_keras'], i) 
    print('\n')
    
print("Ocena modelu tflite na telefonie: \n")
for i in range(0, len(class_names)):
        print('Klasa pozytywna: ', class_names[i], '\n')
        ocen_model_klasyfikacji(dane['class_no'], dane['class_no_phone'], i) 
        print('\n')
        
        
dane_to_analyze = dane.copy()
dane_to_analyze.drop('class_name_keras', axis=1, inplace=True)
dane_to_analyze.drop('class_name_phone', axis=1, inplace=True)
dane_to_analyze.drop('class_true', axis=1, inplace=True)

        
plt.figure(figsize=(60,60))
sns.set(font_scale = 0.9)
sns.heatmap(dane_to_analyze.corr(), cbar = True, annot = True, square = True, cmap = 'Blues')
plt.show()

#ANALIZA BEZ NORMALIZACJI

points_of_interest = [
    'LEFT_ANKLE', 'RIGHT_ANKLE', 'LEFT_WRIST', 'RIGHT_WRIST', 'NOSE', 'LEFT_ELBOW', 'RIGHT_ELBOW', 'LEFT_EAR', 'RIGHT_EAR', 'LEFT_EYE', 'RIGHT_EYE', 'LEFT_HIP', 'RIGHT_HIP', 'LEFT_KNEE', 'RIGHT_KNEE', 'LEFT_SHOULDER', 'RIGHT_SHOULDER', 
]

# Funkcja do generowania wykresów i statystyk dla współrzędnych i score
summary_stats = {}
for point in points_of_interest:
    for coord in ['x', 'y', 'score']:
        keras_col = f"{point}_{coord}_keras"
        phone_col = f"{point}_{coord}_phone"

        if keras_col in dane.columns and phone_col in dane.columns:
            # Wykres punktowy porównania
            plt.figure(figsize=(10, 6))
            sns.scatterplot(x=dane[keras_col], y=dane[phone_col])
            plt.title(f"Porównanie {coord.upper()} dla punktu {point}: Keras vs Phone")
            plt.xlabel(f"{keras_col}")
            plt.ylabel(f"{phone_col}")
            plt.grid(True)
            plt.tight_layout()
            plt.show()

            # Histogram różnic
            diff = abs(dane[keras_col] - dane[phone_col])
            plt.figure(figsize=(10, 6))
            sns.histplot(diff, kde=True, bins=30, color='purple')
            plt.title(f"Różnica {coord.upper()} dla punktu {point} (Keras - Phone)")
            plt.xlabel("Różnica wartości")
            plt.ylabel("Częstotliwość")
            plt.grid(True)
            plt.tight_layout()
            plt.show()

            # Statystyki różnic
            summary_stats[f"{point}_{coord}"] = {
                "mean_difference": diff.mean(),
                "std_difference": diff.std(),
                "min_difference": diff.min(),
                "max_difference": diff.max()
            }

        else:
            print(f"Kolumny dla {coord.upper()} punktu {point} nie znaleziono w danych.")

# Wyświetlanie podsumowania statystyk
print("Podsumowanie statystyk różnic dla współrzędnych i score punktów ciała:")
for key, stats in summary_stats.items():
    print(f"{key}: {stats}")


# Normalizacja danych
scaler = MinMaxScaler()
normalized_data = dane_to_analyze.copy()
columns_to_normalize = [col for col in dane_to_analyze.columns]
normalized_data[columns_to_normalize] = scaler.fit_transform(dane_to_analyze[columns_to_normalize])

points_of_interest = [
    'LEFT_ANKLE', 'RIGHT_ANKLE', 'LEFT_WRIST', 'RIGHT_WRIST', 'NOSE', 'LEFT_ELBOW', 'RIGHT_ELBOW', 'LEFT_EAR', 'RIGHT_EAR', 'LEFT_EYE', 'RIGHT_EYE', 'LEFT_HIP', 'RIGHT_HIP', 'LEFT_KNEE', 'RIGHT_KNEE', 'LEFT_SHOULDER', 'RIGHT_SHOULDER', 
]

# Funkcja do generowania wykresów i statystyk dla współrzędnych i score
summary_stats = {}
for point in points_of_interest:
    for coord in ['x', 'y', 'score']:
        keras_col = f"{point}_{coord}_keras"
        phone_col = f"{point}_{coord}_phone"

        if keras_col in normalized_data.columns and phone_col in normalized_data.columns:
            # Wykres punktowy porównania
            plt.figure(figsize=(10, 6))
            sns.scatterplot(x=normalized_data[keras_col], y=normalized_data[phone_col])
            plt.title(f"Porównanie {coord.upper()} dla punktu {point}: Keras vs Phone (znormalizowane)")
            plt.xlabel(f"{keras_col}")
            plt.ylabel(f"{phone_col}")
            plt.grid(True)
            plt.tight_layout()
            plt.show()

            # Histogram różnic
            diff = abs(normalized_data[keras_col] - normalized_data[phone_col])
            plt.figure(figsize=(10, 6))
            sns.histplot(diff, kde=True, bins=30, color='purple')
            plt.title(f"Różnica {coord.upper()} dla punktu {point} (Keras - Phone, znormalizowane)")
            plt.xlabel("Różnica wartości")
            plt.ylabel("Częstotliwość")
            plt.grid(True)
            plt.tight_layout()
            plt.show()

            # Statystyki różnic
            summary_stats[f"{point}_{coord}"] = {
                "mean_difference": diff.mean(),
                "std_difference": diff.std(),
                "min_difference": diff.min(),
                "max_difference": diff.max()
            }

        else:
            print(f"Kolumny dla {coord.upper()} punktu {point} nie znaleziono w danych.")

# Wyświetlanie podsumowania statystyk
print("Podsumowanie statystyk różnic dla współrzędnych i score punktów ciała (znormalizowane):")
for key, stats in summary_stats.items():
    print(f"{key}: {stats}")
