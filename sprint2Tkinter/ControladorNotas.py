import threading
import tkinter as tk
from io import BytesIO
from tkinter import messagebox
import requests
from PIL import Image,ImageTk


from NotasModel import NotasModel
from VistaNotas import VistaNotas

class ControladorNotas:

    def __init__(self, vista, modelo):
        self.vista = vista
        self.modelo = modelo

         # Vincular eventos
        self.vista.boton_agregar.config(command=self.agregar_nota)
        self.vista.boton_eliminar.config(command=self.eliminar_nota)
        self.vista.boton_guardar.config(command=self.guardar_notas)
        self.vista.boton_cargar.config(command=self.cargar_notas)
        self.vista.boton_descargar_imagen.config(command=self.iniciar_descarga)


        self.actualizar_listbox()

    def agregar_nota(self):
        texto_nota = self.vista.entry_nota.get()
        if texto_nota:
            self.modelo.agregar_nota(texto_nota)
            self.vista.entry_nota.delete(0, 'end')  # Limpiar el campo
            self.actualizar_listbox()

    def eliminar_nota(self):
        indice_seleccionado = self.vista.listbox.curselection()
        if indice_seleccionado:
            self.modelo.eliminar_nota(indice_seleccionado[0])
            self.actualizar_listbox()

    def guardar_notas(self):
        self.modelo.guardar_notas()
        messagebox.showinfo("Guardar Notas", "Notas guardadas con éxito.")

    def cargar_notas(self):
        self.modelo.cargar_notas()
        self.actualizar_listbox()

    def descargar_imagen(self, url, callback):
        try:
            respuesta = requests.get(url)
            respuesta.raise_for_status()
            imagen = Image.open(BytesIO(respuesta.content))
            imagen_tk = ImageTk.PhotoImage(imagen)
            # Programar la actualización de la interfaz en el hilo principal
            self.vista.ventana.after(0, callback, imagen_tk)
        except requests.exceptions.RequestException as e:
            print(f"Error al descargar la imagen: {e}")
            self.vista.ventana.after(0, callback, None)

    def actualizar_etiqueta(self, imagen_tk):
        if imagen_tk:
            self.vista.label_imagen.config(image=imagen_tk)
            self.vista.label_imagen = imagen_tk  # Mantener una referencia
        else:
            self.vista.etiqueta.config(text="Error al descargar la imagen.")

    def iniciar_descarga(self):
        url = 'https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen.jpg'
        hilo = threading.Thread(target=self.descargar_imagen, args=(url, self.actualizar_etiqueta))
        hilo.start()




    def actualizar_coordenadas(self, event):
        x, y = event.x, event.y
        self.vista.label_coordenadas.config(text=f"Coordenadas: ({x}, {y})")

    def actualizar_listbox(self):
        self.vista.listbox.delete(0, 'end')
        for nota in self.modelo.obtener_notas():
            self.vista.listbox.insert('end', nota)
