import tkinter as tk

from controlador import GameController
from modelo import GameModel


def main():
    root = tk.Tk()
    root.withdraw()  # Ocultar la ventana principal temporalmente

    model = GameModel()
    controller = GameController(root, model)  # Inicializamos el controlador
    controller.view.show()  # Mostrar la vista del menú principal

    root.mainloop()

if __name__ == "__main__":
    main()
