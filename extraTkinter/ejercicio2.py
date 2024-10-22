# crear ventana que vaya al centro de la pantalla y se ajuste al contenido

import tkinter as tk

def centrar_ventana(ventana):
    ventana.update_idletasks()
    ancho = ventana.winfo_width()
    alto = ventana.winfo_height()
    x = (ventana.winfo_screenwidth() // 2) - (ancho // 2)
    y = (ventana.winfo_screenheight() // 2) - (alto // 2)
    ventana.geometry('{}x{}+{}+{}'.format(ancho, alto, x, y))

# Crear la ventana principal
root = tk.Tk()
root.title("Ventana Centrada")

# Ajustar al contenido
frame = tk.Frame(root)
frame.pack()

# Contenido de ejemplo
label = tk.Label(frame, text="Esta ventana está centrada en la pantalla y ajustada al contenido.")
label.pack(padx=10, pady=10)

# Centrar la ventana
root.update()
centrar_ventana(root)

root.mainloop()
