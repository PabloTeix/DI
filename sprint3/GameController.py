from tkinter import messagebox
from tkinter.simpledialog import askstring

from vista import MainMenu


class GameController:
    def __init__(self, root):
        self.root = root  # La ventana principal
        self.menu = MainMenu(self.root, self)  # Inicializamos el menú

        self.player_name = ""  # Almacenará el nombre del jugador
        self.difficulty = ""  # Almacenará la dificultad seleccionada


    def start_game_callback(self):
        # Al hacer clic en "Jugar", pedimos la dificultad y el nombre
        self.show_difficulty_selection()

    def start_game_callback(self):
        # Al hacer clic en "Jugar", pedimos la dificultad y el nombre
        self.show_difficulty_selection()

    def show_difficulty_selection(self):
        # Mostrar un cuadro de diálogo para seleccionar la dificultad
        difficulty = askstring("Seleccionar dificultad", "Elige una dificultad (facil, medio, dificil):")

        # Validar la dificultad
        while difficulty not in ['facil', 'medio', 'dificil']:
            difficulty = askstring("Seleccionar dificultad",
                                   "Opción inválida. Elige una dificultad (facil, medio, dificil):")

        self.difficulty = difficulty  # Guardar la dificultad seleccionada

        # Pedir el nombre del jugador
        self.ask_player_name()

    def ask_player_name(self):
        # Pedir el nombre del jugador
        player_name = askstring("Nombre del jugador", "Introduce tu nombre:")

        if player_name:  # Asegurarse de que el jugador ha introducido un nombre
            self.player_name = player_name
            print(f"Jugador: {self.player_name}")
            print(f"Dificultad seleccionada: {self.difficulty}")
        else:
            messagebox.showwarning("Nombre no válido", "Debes introducir un nombre.")
            self.ask_player_name()  # Pedir nuevamente el nombre si no es válido

    def show_difficulty_selection(self):
        # Mostrar un cuadro de diálogo para seleccionar la dificultad
        difficulty = askstring("Seleccionar dificultad", "Elige una dificultad (facil, medio, dificil):")

        # Validar la dificultad
        while difficulty not in ['facil', 'medio', 'dificil']:
            difficulty = askstring("Seleccionar dificultad",
                                   "Opción inválida. Elige una dificultad (facil, medio, dificil):")

        self.difficulty = difficulty  # Guardar la dificultad seleccionada

        # Pedir el nombre del jugador
        self.ask_player_name()

    def ask_player_name(self):
        # Pedir el nombre del jugador
        player_name = askstring("Nombre del jugador", "Introduce tu nombre:")

        if player_name:  # Asegurarse de que el jugador ha introducido un nombre
            self.player_name = player_name
            print(f"Jugador: {self.player_name}")
            print(f"Dificultad seleccionada: {self.difficulty}")
        else:
            messagebox.showwarning("Nombre no válido", "Debes introducir un nombre.")
            self.ask_player_name()  # Pedir nuevamente el nombre si no es válido

    def show_stats_callback(self):
        print("Mostrando estadísticas...")
        # Aquí se podría implementar la lógica para mostrar las estadísticas

    def quit_callback(self):
        print("Saliendo del juego...")
        self.root.quit()  # Cierra la aplicación
