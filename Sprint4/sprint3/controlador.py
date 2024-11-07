import threading
import tkinter as tk
from tkinter import simpledialog, messagebox
from vista import GameView, MainMenu
from modelo import GameModel

class GameController:
    def __init__(self, root):
        self.root = root
        self.model = None
        self.view = None
        self.selected = []
        self.timer_started = False
        self.main_menu = MainMenu(root, self.show_difficulty_selection, self.show_stats, self.quit_game)

    def show_difficulty_selection(self):
        """Solicita al jugador la dificultad"""
        difficulty = simpledialog.askstring("Dificultad", "Elige dificultad (facil, medio, dificil):")
        if difficulty in ["facil", "medio", "dificil"]:
            player_name = self.main_menu.ask_player_name()
            if player_name:  # Solo continuar si el jugador ha ingresado un nombre
                self.start_game(difficulty, player_name)

    def start_game(self, difficulty, player_name):
        """Inicia una nueva partida"""
        self.model = GameModel(difficulty, player_name)
        self.selected = []  # Resetear las cartas seleccionadas
        self.model.start_timer()  # Iniciar el temporizador

        # Crear la vista del tablero de juego
        self.view = GameView(self.on_card_click, self.update_move_count, self.update_time)
        self.view.create_board(self.model)

        # Mostrar ventana de carga mientras se cargan las imágenes
        self.show_loading_window()

    def show_loading_window(self):
        """Muestra una ventana de carga mientras las imágenes se cargan"""
        loading_window = tk.Toplevel(self.root)
        loading_window.title("Cargando...")

        loading_label = tk.Label(loading_window, text="Cargando imágenes. Por favor espera...")
        loading_label.pack(padx=20, pady=20)

        # Esperar hasta que las imágenes estén cargadas
        def wait_for_images():
            while not self.model.images_are_loaded():
                self.root.update_idletasks()
            loading_window.destroy()  # Cerrar la ventana de carga cuando todo esté listo
            self.update_time()  # Actualizar el tiempo desde que se inició el juego

        loading_thread = threading.Thread(target=wait_for_images)
        loading_thread.start()

    def on_card_click(self, pos):
        """Maneja el evento de hacer clic en una carta"""
        if len(self.selected) < 2:
            self.selected.append(pos)

            # Mostrar la carta en la posición seleccionada
            self.view.update_board(pos, self.model.image_ids[self.model.board[pos]])

            if len(self.selected) == 2:
                # Verificar si las cartas seleccionadas coinciden
                pos1, pos2 = self.selected
                match = self.model.check_match(pos1, pos2)
                self.view.update_move_count(self.model.moves)

                if match:
                    if self.model.is_game_complete():
                        self.end_game()
                else:
                    # Si no coinciden, esperar un segundo y luego restaurar las cartas
                    self.root.after(1000, self.restore_cards, pos1, pos2)

                self.selected = []  # Resetear selección

    def restore_cards(self, pos1, pos2):
        """Restaura las cartas a su estado oculto si no coinciden"""
        self.view.reset_cards(pos1, pos2)

    def update_move_count(self, moves):
        """Actualiza el contador de movimientos en la vista"""
        self.view.update_move_count(moves)

    def update_time(self):
        """Actualiza el tiempo transcurrido en la vista"""
        elapsed_time = self.model.get_time()
        self.view.update_time(elapsed_time)
        if not self.model.is_game_complete():
            self.root.after(100, self.update_time)  # Continuar actualizando cada 100ms

    def end_game(self):
        """Muestra el mensaje de fin de juego y guarda la puntuación"""
        messagebox.showinfo("¡Juego terminado!", f"¡Felicidades, {self.model.player_name}!\nHas completado el juego en {self.model.moves} movimientos y {self.model.get_time()} segundos.")
        self.model.save_score()  # Guardar la puntuación
        self.show_stats()

    def show_stats(self):
        """Muestra las estadísticas del jugador"""
        stats = self.model.load_scores()
        self.main_menu.show_stats(stats)

    def quit_game(self):
        """Cierra la aplicación"""
        self.root.quit()
