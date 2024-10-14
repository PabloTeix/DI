# Importamos el módulo tkinter con el alias tk
import tkinter as tk
# Importamos el módulo messagebox de tkinter
from tkinter import messagebox

# Definimos la función cambiar_mensaje que cambia el texto de etiqueta3
def cambiar_mensaje():
   etiqueta3.config(text="Texto modificado")

# Creamos la ventana principal
root = tk.Tk()
root.title("Ventana")  # Establecemos el título de la ventana
root.geometry("300x200")  # Establecemos el tamaño de la ventana

# Creamos y empaquetamos las etiquetas
etiqueta1 = tk.Label(root, text="Bienvenido")
etiqueta1.pack()
etiqueta2 = tk.Label(root, text="Pablo")
etiqueta2.pack()
etiqueta3 = tk.Label(root, text="Texto")
etiqueta3.pack()

# Creamos y empaquetamos el botón, asignando la función cambiar_mensaje al comando
boton = tk.Button(root, text="Cliquea aqui", command=cambiar_mensaje, bg="black", fg="white")
boton.pack(pady=50)

# Iniciamos el bucle principal de la aplicación
root.mainloop()