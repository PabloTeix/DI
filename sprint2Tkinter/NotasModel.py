import tkinter as tk
import os

class NotasModel:
    def __init__(self):
        self.notas=[]
        self.cargar_notas()

    def agregar_nota(self,nueva_nota):
        print("Añade nueva nota")
        self.notas.append(nueva_nota)

    def eliminar_nota(self,indice):
        print("elimina nota")
        if 0 <= indice<len(self.notas):
            del self.notas[indice]

    def obtener_notas(self):
        return self.notas

    def guardar_notas(self):
        print("guardar notas")
        with open('notas.txt','w') as archivo:
            for nota in self.notas:
                archivo.write(nota + '\n')

    def cargar_notas(self):
        print("cargar notas")
        try:
            with open('notas.txt','r') as archivo:
                self.notas=[linea.strip() for linea in archivo]
        except FileNotFoundError:
            self.notas=[]






































