import tkinter as tk
from tkinter import messagebox

from vista import GameView, MainMenu


class GameController:
    def __init__(self, root, model):
        self.root = root
        self.model = model
        self.view = MainMenu(root, self)  # Vista del menú principal
        self.game_view = None  # Vista del juego, aún no inicializada

    def start_game_callback(self):
        """Llamado cuando el jugador inicia el juego desde el menú."""
        difficulty = self.view.ask_difficulty()
        if difficulty:
            self.model._generate_board(difficulty)
            self.game_view = GameView(self.root, self)  # Crear una nueva instancia de GameView
            self.view.root.withdraw()  # Ocultar el menú principal
            loading_window = self.game_view.show_loading_window()
            # Simulación de carga de imágenes
            self.model.load_images_thread(["https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen1.jpg", "https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen2.jpg","https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen3.jpg","https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen4.jpg","https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen5.jpg","https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/imagen6.jpg"],
                                          lambda: self.on_images_loaded(loading_window))

    def show_stats_callback(self):
        """Llamado para mostrar estadísticas."""
        messagebox.showinfo("Estadísticas", "Aquí van las estadísticas del jugador.")

    def quit_callback(self):
        """Cerrar la aplicación."""
        self.root.quit()

    def on_images_loaded(self, loading_window):
        """Callback cuando las imágenes han sido cargadas."""
        loading_window.destroy()  # Cerrar la ventana de carga
        self.game_view.create_board(self.model)  # Crear el tablero de juego
        self.view.root.deiconify()  # Volver a mostrar el menú principal
        # Aquí puedes hacer más cosas después de que las imágenes se hayan cargado (como iniciar el juego)

    def flip_card(self, card_index):
        """Llamado cuando se voltea una carta."""
        flipped_image = self.model.flip_card(card_index)
        if flipped_image:
            self.game_view.update_card(card_index, flipped_image)
