import tkinter as tk
from PIL import Image, ImageTk

class VistaNotas:
    def __init__(self):
        self.ventana = tk.Tk()
        self.ventana.title("Gestión de Notas")

        self.label_titulo = tk.Label(self.ventana, text="Notas", font=("Arial", 24))
        self.label_titulo.pack()

        self.listbox = tk.Listbox(self.ventana)
        self.listbox.pack()

        self.entry_nota = tk.Entry(self.ventana)
        self.entry_nota.pack()

        self.boton_agregar = tk.Button(self.ventana, text="Agregar Nota")
        self.boton_agregar.pack()

        self.boton_eliminar = tk.Button(self.ventana, text="Eliminar Nota")
        self.boton_eliminar.pack()

        self.boton_guardar = tk.Button(self.ventana, text="Guardar Notas")
        self.boton_guardar.pack()

        self.boton_cargar = tk.Button(self.ventana, text="Cargar Notas")
        self.boton_cargar.pack()

        self.boton_descargar_imagen = tk.Button(self.ventana, text="Descargar Imagen")
        self.boton_descargar_imagen.pack()

        self.label_coordenadas = tk.Label(self.ventana, text="Coordenadas: (0, 0)")
        self.label_coordenadas.pack()

        self.label_imagen = tk.Label(self.ventana)
        self.label_imagen.pack()



    def actualizar_listbox(self, notas):
        self.listbox.delete(0, 'end')
        for nota in notas:
            self.listbox.insert('end', nota)

    def mainloop(self):
        self.ventana.bind("<Button-1>", self.bind_event)
        self.ventana.mainloop()

    def bind_event(self, event):
        self.label_coordenadas.config(text=f"Coordenadas: ({event.x}, {event.y})")
