from vista import MainMenu


class GameController:
    def __init__(self, root):
        self.root = root  # La ventana principal
        self.menu = MainMenu(self.root, self)  # Inicializamos el menú

    def start_game_callback(self):
        print("Iniciando el juego...")
        # Aquí podemos implementar la lógica para iniciar el juego

    def show_stats_callback(self):
        print("Mostrando estadísticas...")
        # Aquí se podría implementar la lógica para mostrar las estadísticas

    def quit_callback(self):
        print("Saliendo del juego...")
        self.root.quit()  # Cierra la aplicación
