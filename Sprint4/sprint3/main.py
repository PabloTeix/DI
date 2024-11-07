import tkinter as tk
from game_controller import GameController

def main():
    root = tk.Tk()

    # Crear el controlador
    controller = GameController(root)

    # No necesitamos ocultar la ventana principal, así que eliminamos root.withdraw()

    # Mostrar el menú principal
    root.mainloop()

if __name__ == "__main__":
    main()
