import tkinter as tk


def dibujar():
    # Limpiamos el canvas antes de dibujar
    canvas.delete("all")

    # Obtenemos las coordenadas y el tamaño del campo de entrada
    coords = entry_coords.get().split(',')
    x1, y1, x2, y2 = map(int, coords)
    radio = int(entry_radio.get())

    # Calculamos el centro del rectángulo
    centro_x = (x1 + x2) // 2
    centro_y = (y1 + y2) // 2

    # Dibujamos el rectángulo
    canvas.create_rectangle(x1, y1, x2, y2, outline="blue", width=2)

    # Dibujamos el círculo en el centro del rectángulo
    canvas.create_oval(centro_x - radio, centro_y - radio, centro_x + radio, centro_y + radio, outline="red", width=2)


# Creamos la ventana principal
root = tk.Tk()
root.title("Ejercicio 7: Canvas")
root.geometry("400x400")

# Creamos el Canvas
canvas = tk.Canvas(root, width=300, height=300, bg="white")
canvas.pack(pady=20)

# Creamos los campos de entrada para las coordenadas y el radio
tk.Label(root, text="Coordenadas del rectángulo (x1,y1,x2,y2):").pack()
entry_coords = tk.Entry(root)
entry_coords.pack()

tk.Label(root, text="Radio del círculo:").pack()
entry_radio = tk.Entry(root)
entry_radio.pack()

# Creamos el botón para dibujar
boton = tk.Button(root, text="Dibujar", command=dibujar)
boton.pack(pady=10)

# Iniciamos el bucle principal de la aplicación
root.mainloop()
