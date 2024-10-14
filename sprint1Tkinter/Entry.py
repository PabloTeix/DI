
# Importamos el módulo tkinter con el alias tk
import tkinter as tk

# Definimos la función saludar que toma el nombre del campo de entrada y lo muestra en la etiqueta
def saludar():
    nombre = entrada.get()
    etiqueta_resultado.config(text=f"Bienvenido: {nombre}")

# Creamos la ventana principal
root = tk.Tk()
root.title("Ejercicio 3: Entry")  # Establecemos el título de la ventana
root.geometry("300x200")  # Establecemos el tamaño de la ventana

# Creamos y empaquetamos la etiqueta de instrucción
etiqueta = tk.Label(root, text="Escribe tu nombre:")
etiqueta.pack(pady=10)

# Creamos y empaquetamos el campo de entrada
entrada = tk.Entry(root, width=30)
entrada.pack(pady=5)

# Creamos y empaquetamos el botón que muestra el saludo personalizado
boton = tk.Button(root, text="Saludo personalizado", command=saludar)
boton.pack(pady=10)

# Creamos y empaquetamos la etiqueta para mostrar el resultado del saludo
etiqueta_resultado = tk.Label(root, text="")
etiqueta_resultado.pack(pady=10)

# Iniciamos el bucle principal de la aplicación
root.mainloop()
