import tkinter as tk

# Función para mostrar un mensaje en la etiqueta
def mostrar_mensaje():
    etiqueta.config(text="¡Botón presionado!")

# Función para cerrar la ventana
def cerrar_ventana():
    root.destroy()

# Crear la ventana principal
root = tk.Tk()
root.title("Ejercicio 2: Botones")
root.geometry("300x200")

# Crear y empaquetar la etiqueta
etiqueta = tk.Label(root, text="")
etiqueta.pack(pady=20)

# Crear y empaquetar el botón que muestra un mensaje
boton_mensaje = tk.Button(root, text="Mostrar mensaje", command=mostrar_mensaje)
boton_mensaje.pack(pady=10)

# Crear y empaquetar el botón que cierra la ventana
boton_cerrar = tk.Button(root, text="Cerrar ventana", command=cerrar_ventana)
boton_cerrar.pack(pady=10)

# Iniciar el bucle principal de la aplicación
root.mainloop()