# Importamos el módulo tkinter con el alias tk
import tkinter as tk


# Definimos la función mostrar_seleccion que actualiza la etiqueta según el estado de los Checkbuttons
def mostrar_seleccion():
    seleccion1 = var_check1.get()
    seleccion2 = var_check2.get()
    seleccion3 = var_check3.get()

    # Determinamos el estado de cada Checkbutton
    estado1 = "seleccionado" if seleccion1 else "no seleccionado"
    estado2 = "seleccionado" if seleccion2 else "no seleccionado"
    estado3 = "seleccionado" if seleccion3 else "no seleccionado"

    # Actualizamos el texto de la etiqueta con los estados de los Checkbuttons
    etiqueta.config(text=f"Leer: {estado1}, Deporte: {estado2}, Música: {estado3}")


# Creamos la ventana principal
root = tk.Tk()
root.title("Ejercicio 4: CheckButton")  # Establecemos el título de la ventana
root.geometry("500x500")  # Establecemos el tamaño de la ventana

# Creamos variables IntVar para almacenar el estado de cada Checkbutton
var_check1 = tk.IntVar()
var_check2 = tk.IntVar()
var_check3 = tk.IntVar()

# Creamos y empaquetamos los Checkbuttons, asignando variables y comando
check1 = tk.Checkbutton(root, text="Leer", variable=var_check1, command=mostrar_seleccion)
check1.pack(pady=10)
check2 = tk.Checkbutton(root, text="Deporte", variable=var_check2, command=mostrar_seleccion)
check2.pack(pady=10)
check3 = tk.Checkbutton(root, text="Música", variable=var_check3, command=mostrar_seleccion)
check3.pack(pady=10)

# Creamos y empaquetamos la etiqueta para mostrar el estado de los Checkbuttons
etiqueta = tk.Label(root, text="CheckButton: No seleccionado")
etiqueta.pack(pady=10)

# Iniciamos el bucle principal de la aplicación
root.mainloop()