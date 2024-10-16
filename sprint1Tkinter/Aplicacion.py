# Desarrolla una aplicación que permita registrar usuarios con los siguientes datos: nombre,
# edad y género. La aplicación debe contar con las siguientes funcionalidades:
# 1. Un campo de entrada (Entry) para el nombre del usuario.
# 2. Una barra deslizante (Scale) para seleccionar la edad del usuario (entre 0 y 100 años).
# 3. Tres botones de opción (Radiobutton) para seleccionar el género del usuario (masculino, femenino, otro).
# 4. Un botón (Button) para añadir el usuario a una lista.
# 5. Una lista de usuarios (Listbox) que muestre el nombre, la edad y el género de los usuarios registrados.
# 6. Una barra de desplazamiento vertical (Scrollbar) para la lista de usuarios.
# 7. Un botón para eliminar el usuario seleccionado de la lista.
# 8. Un botón de salir que cierre la aplicación.
# 9. Un menú desplegable con las opciones “Guardar Lista” y “Cargar Lista”;
# esto nos mostrará un messagebox indicando que la lista ha sido guardada o cargada.

import json
import tkinter as tk
from tkinter import messagebox, filedialog

# Función para mostrar la edad seleccionada en la etiqueta correspondiente
def mostrar_edad(ed):
    edad.config(text=f"Edad: {ed}")

# Función para mostrar el género seleccionado en la etiqueta correspondiente
def mostrar_genero():
    generoselecciondo = selgen.get()  # Obtenemos el valor seleccionado
    genero.config(text=f"Seleccionaste: {generoselecciondo}")  # Actualizamos el texto de la etiqueta

# Función para añadir el usuario a la lista
def añadir_usuario():
    nombre = entrada_nombre.get()
    ed = scale.get()
    genero_seleccionado = selgen.get()
    if nombre:
        lista_usuarios.insert(tk.END, f"{nombre}, {ed}, {genero_seleccionado}")
        entrada_nombre.delete(0, tk.END)
    else:
        messagebox.showwarning("Advertencia", "El nombre no puede estar vacío")

# Función para eliminar el usuario seleccionado de la lista
def eliminar_usuario():
    seleccion = lista_usuarios.curselection()
    if seleccion:
        lista_usuarios.delete(seleccion)
    else:
        messagebox.showwarning("Advertencia", "Selecciona un usuario para eliminar")

# Función para salir de la aplicación
def salir():
    root.destroy()

# Función para guardar la lista de usuarios en un archivo
def guardar_lista():
    lista = lista_usuarios.get(0, tk.END)
    archivo = filedialog.asksaveasfile(mode='w', defaultextension=".json", filetypes=(("JSON files", "*.json"), ("All files", "*.*")))
    if archivo:
        json.dump(lista, archivo)
        archivo.close()
        messagebox.showinfo("Guardar Lista", "La lista ha sido guardada")

# Función para cargar la lista de usuarios desde un archivo
def cargar_lista():
    archivo = filedialog.askopenfile(mode='r', filetypes=(("JSON files", "*.json"), ("All files", "*.*")))
    if archivo:
        lista = json.load(archivo)
        lista_usuarios.delete(0, tk.END)
        for item in lista:
            lista_usuarios.insert(tk.END, item)
        archivo.close()
        messagebox.showinfo("Cargar Lista", "La lista ha sido cargada")

# Creamos la ventana principal
root = tk.Tk()
root.title("Aplicacion")  # Establecemos el título de la ventana
root.geometry("500x500")  # Establecemos el tamaño de la ventana

# Campo de entrada para el nombre
n=tk.Label(root, text="Nombre")
n.pack()
entrada_nombre = tk.Entry(root)
entrada_nombre.pack()

# Barra deslizante para seleccionar la edad
scale = tk.Scale(root, from_=0, to=100, orient='horizontal', command=mostrar_edad)
scale.pack(pady=5)
edad = tk.Label(root, text="Edad")
edad.pack(pady=5)

# Selección de género mediante botones de opción
selgen = tk.StringVar()
selgen.set("Ninguna")  # Valor por defecto
tk.Radiobutton(root, text="Masculino", variable=selgen, value="masculino", command=mostrar_genero).pack(pady=2)
tk.Radiobutton(root, text="Femenino", variable=selgen, value="femenino", command=mostrar_genero).pack(pady=2)
tk.Radiobutton(root, text="Otro", variable=selgen, value="otro", command=mostrar_genero).pack(pady=2)

# Creamos una etiqueta para mostrar la selección actual de género
genero = tk.Label(root, text="Género: No seleccionado")
genero.pack(pady=5)

# Botón para añadir el usuario
tk.Button(root, text="Añadir Usuario", command=añadir_usuario).pack(pady=10)

# Lista de usuarios con scrollbar
frame_lista = tk.Frame(root)
frame_lista.pack(fill='both', expand=True)
scrollbar = tk.Scrollbar(frame_lista, orient='vertical')
scrollbar.pack(side='right', fill='y')
lista_usuarios = tk.Listbox(frame_lista, yscrollcommand=scrollbar.set)
lista_usuarios.pack(fill='both', expand=True)
scrollbar.config(command=lista_usuarios.yview)

# Botón para eliminar el usuario seleccionado
tk.Button(root, text="Eliminar Usuario", command=eliminar_usuario).pack()

# Botón para salir
tk.Button(root, text="Salir", command=salir).pack()

# Menú desplegable
menu_principal = tk.Menu(root)
root.config(menu=menu_principal)
menu_archivo = tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Menú", menu=menu_archivo)
menu_archivo.add_command(label="Guardar Lista", command=guardar_lista)
menu_archivo.add_command(label="Cargar Lista", command=cargar_lista)

# Iniciamos el bucle principal de la aplicación
root.mainloop()
