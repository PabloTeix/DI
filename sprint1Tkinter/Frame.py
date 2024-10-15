import tkinter as tk

def mostrar_texto():
    texto = entry.get()
    etiqueta_mostrar.config(text=texto)

def borrar_texto():
    entry.delete(0, tk.END)
    etiqueta_mostrar.config(text="")

# Creamos la ventana principal
root = tk.Tk()
root.title("Interfaz con Frames")
root.geometry("400x200")

# Creamos el Frame superior
frame_superior = tk.Frame(root)
frame_superior.pack(pady=10)

# Añadimos dos etiquetas y un campo de entrada al Frame superior
tk.Label(frame_superior, text="Etiqueta 1:").grid(row=0, column=0, padx=5, pady=5)
tk.Label(frame_superior, text="Etiqueta 2:").grid(row=1, column=0, padx=5, pady=5)
entry = tk.Entry(frame_superior)
entry.grid(row=0, column=1, rowspan=2, padx=5, pady=5)

# Creamos el Frame inferior
frame_inferior = tk.Frame(root)
frame_inferior.pack(pady=10)

# Añadimos dos botones al Frame inferior
boton_mostrar = tk.Button(frame_inferior, text="Mostrar", command=mostrar_texto)
boton_mostrar.grid(row=0, column=0, padx=5, pady=5)
boton_borrar = tk.Button(frame_inferior, text="Borrar", command=borrar_texto)
boton_borrar.grid(row=0, column=1, padx=5, pady=5)

# Creamos una etiqueta para mostrar el contenido del Entry
etiqueta_mostrar = tk.Label(root, text="")
etiqueta_mostrar.pack(pady=10)

# Iniciamos el bucle principal de la aplicación
root.mainloop()
