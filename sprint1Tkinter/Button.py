import tkinter as tk
from tkinter import messagebox

def mostrar_mensaje():
    messagebox.showinfo("Mensaje","Primer mensaje")
root = tk.Tk()
root.title("Ejercicio2")
root.geometry("300x200")

boton1 = tk.Button(root, text="Mostar mensaje", command=mostrar_mensaje, bg="black", fg="white")
boton1.pack(pady=50)



root.mainloop()