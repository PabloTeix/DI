import tkinter as tk
from tkinter import simpledialog

from functools import partial
from PIL import ImageTk, Image


class MainMenu:
    def __init__(self, root, controller):
        self.root = root
        self.controller = controller

        # Título de la ventana
        self.root.title("Juego de Memoria")

        # Crear botones para cada opción
        self.start_button = tk.Button(self.root, text="Jugar", command=self.controller.start_game_callback)
        self.start_button.pack(pady=10)

        self.stats_button = tk.Button(self.root, text="Estadísticas", command=self.controller.show_stats_callback)
        self.stats_button.pack(pady=10)

        self.quit_button = tk.Button(self.root, text="Salir", command=self.controller.quit_callback)
        self.quit_button.pack(pady=10)

    def show(self):
        self.root.deiconify()  # Mostrar la ventana

    def ask_player_name(self):
        return simpledialog.askstring("Nombre del jugador", "¿Cuál es tu nombre?")

    def ask_difficulty(self):
        difficulty = simpledialog.askstring("Selección de dificultad",
                                            "Elige dificultad: fácil, medio, difícil")
        if difficulty is None:
            return None
        return difficulty.lower() if difficulty.lower() in ["facil", "medio", "dificil"] else None





class GameView:

    def __init__(self, root, controller):
        self.root = root
        self.controller = controller
        self.board_window = None
        self.board_frame = None
        self.card_buttons = []

    def show_loading_window(self):
        loading_window = tk.Toplevel(self.root)
        loading_window.title("Cargando...")
        loading_label = tk.Label(loading_window, text="Cargando imágenes...")
        loading_label.pack(pady=20)
        return loading_window

    def create_board(self, model):
        """Crear el tablero con las cartas dadas vuelta inicialmente."""
        self.board_window = tk.Toplevel(self.root)
        self.board_window.title("Tablero de Juego")

        self.board_frame = tk.Frame(self.board_window)
        self.board_frame.pack()

        # Crear los botones para las cartas, todos con la imagen de la carta dada vuelta
        self.card_buttons = []
        for idx, _ in enumerate(model.board):
            # Usar partial para pasar el idx correctamente
            card_button = tk.Button(self.board_frame, image=model.get_card_back(),
                                    command=partial(self.controller.flip_card, idx))
            card_button.grid(row=idx // 4, column=idx % 4, padx=5, pady=5)
            self.card_buttons.append(card_button)

    def update_card(self, card_index, flipped_image):
        """Actualizar la imagen de la carta cuando se voltea."""
        card_button = self.card_buttons[card_index]
        card_button.config(image=flipped_image)
