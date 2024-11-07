import tkinter as tk
from vista import MainMenu  # Asegúrate de que esta ruta sea correcta
from GameController import GameController  # Asegúrate de que esta ruta sea correcta

def main():
    root = tk.Tk()  # Crear la ventana principal
    controller = GameController(root)  # Crear el controlador
    root.mainloop()  # Iniciar el bucle de la interfaz gráfica

if __name__ == "__main__":
    main()

#probando