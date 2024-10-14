# Importamos el módulo tkinter con el alias tk
import tkinter as tk

# Definimos la función que se ejecutará cuando se seleccione un RadioButton
def mostrar_seleccion():
    seleccion = var_radio.get()  # Obtenemos el valor seleccionado
    etiqueta.config(text=f"Seleccionaste: {seleccion}")  # Actualizamos el texto de la etiqueta
    # Cambiamos el color de fondo según la selección
    if seleccion == "rojo":
        root.config(bg="red")
    elif seleccion == "verde":
        root.config(bg="green")
    elif seleccion == "azul":
        root.config(bg="blue")

# Creamos la ventana principal
root = tk.Tk()
root.title("Ejercicio 5: RadioButton")  # Establecemos el título de la ventana
root.geometry("300x200")  # Establecemos el tamaño de la ventana

# Creamos una variable StringVar para almacenar la selección del RadioButton
var_radio = tk.StringVar()
var_radio.set("Ninguna")  # Valor inicial de la variable

# Creamos los RadioButtons y los añadimos a la ventana
radio1 = tk.Radiobutton(root, text="rojo",
                        variable=var_radio, value="rojo",
                        command=mostrar_seleccion)
radio1.pack(pady=5)

radio2 = tk.Radiobutton(root, text="verde",
                        variable=var_radio, value="verde",
                        command=mostrar_seleccion)
radio2.pack(pady=5)

radio3 = tk.Radiobutton(root, text="azul",
                        variable=var_radio, value="azul",
                        command=mostrar_seleccion)
radio3.pack(pady=5)

# Creamos una etiqueta para mostrar la selección actual
etiqueta = tk.Label(root, text="RadioButton: No seleccionado")
etiqueta.pack(pady=10)

# Iniciamos el bucle principal de la aplicación
root.mainloop()
