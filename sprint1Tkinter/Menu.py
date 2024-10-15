# Importamos las librerías necesarias
import tkinter as tk
from idlelib.mainmenu import menudefs
from tkinter import messagebox

# Función para mostrar un mensaje informativo al abrir una nueva ventana
def nueva_ventana():
    messagebox.showinfo("Nuevo", "Abrir una nueva ventana")

# Función para salir de la aplicación
def salir():
    root.quit()

# Creamos la ventana principal
root = tk.Tk()
root.title("Menu")
root.geometry("400x200")

# Configuramos el menú principal
menu_principal = tk.Menu(root)
root.config(menu=menu_principal)

# Crear submenu Archivo
menu_archivo = tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Archivo", menu=menu_archivo)
menu_archivo.add_command(label="Abrir", command=nueva_ventana)  # Opción para abrir una nueva ventana
menu_archivo.add_separator()  # Separador visual
menu_archivo.add_command(label="Salir", command=salir)  # Opción para salir de la aplicación

# Submenu Ayuda
def mostrarayuda():
    messagebox.showinfo("Ayuda", "esto es un mensaje informativo")

menuayuda = tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Ayuda", menu=menuayuda)
menuayuda.add_command(label="Acerca de", command=mostrarayuda)  # Opción para mostrar un mensaje de ayuda

# Iniciamos el bucle principal de la aplicación
root.mainloop()
