# Importamos el módulo tkinter con el alias tk
import tkinter as tk

# Definimos la función que se ejecutará cuando se presione el botón
def mostrar_seleccion():
    seleccion = listbox.curselection()  # Obtenemos las posiciones seleccionadas en la ListBox
    elementos = [listbox.get(i) for i in seleccion]  # Obtenemos los elementos seleccionados
    etiqueta.config(text=f"Seleccionaste: {', '.join(elementos)}")  # Actualizamos el texto de la etiqueta

# Creamos la ventana principal
root = tk.Tk()
root.title("Ejercicio 6: ListBox")  # Establecemos el título de la ventana
root.geometry("300x250")  # Establecemos el tamaño de la ventana

# Definimos las opciones que se mostrarán en la ListBox
opciones = ["Manzana", "Banana", "Naranja"]

# Creamos la ListBox y añadimos las opciones
listbox = tk.Listbox(root, selectmode=tk.MULTIPLE)  # Permitimos selección múltiple
for opcion in opciones:
    listbox.insert(tk.END, opcion)  # Insertamos cada opción en la ListBox
listbox.pack(pady=10)  # Añadimos un margen vertical

# Creamos un botón que llamará a la función mostrar_seleccion al ser presionado
boton = tk.Button(root, text="Mostrar Selección", command=mostrar_seleccion)
boton.pack(pady=5)  # Añadimos un margen vertical

# Creamos una etiqueta para mostrar la selección actual
etiqueta = tk.Label(root, text="Seleccionaste: Ninguno")
etiqueta.pack(pady=10)  # Añadimos un margen vertical

# Iniciamos el bucle principal de la aplicación
root.mainloop()
