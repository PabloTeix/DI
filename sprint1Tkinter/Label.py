import tkinter as tk
from tkinter import messagebox

def cambiar_mensaje():
   etiqueta3.config(text="Texto modificado")


root = tk.Tk()
root.title("Ventana")
root.geometry("300x200")
etiqueta1 = tk.Label(root, text="Bienvenido")
etiqueta1.pack()
etiqueta2 = tk.Label(root, text="Pablo")
etiqueta2.pack()
etiqueta3 = tk.Label(root, text="Texto")
etiqueta3.pack()

boton = tk.Button(root, text="Cliquea aqui", command=cambiar_mensaje, bg="black", fg="white")
boton.pack(pady=50)

root.mainloop()