import tkinter as tk


class MainMenu:
    def __init__(self, root, controller):
        self.root = root  # La ventana principal
        self.controller = controller  # El controlador que gestionará las acciones de los botones

        # Configuración de la ventana
        self.root.title("Menú Principal")
        self.root.geometry("300x200")  # Tamaño de la ventana

        # Crear los botones del menú
        self.play_button = tk.Button(self.root, text="Jugar", width=20, command=self.controller.start_game_callback)
        self.play_button.pack(pady=10)

        self.stats_button = tk.Button(self.root, text="Estadísticas", width=20,
                                      command=self.controller.show_stats_callback)
        self.stats_button.pack(pady=10)

        self.quit_button = tk.Button(self.root, text="Salir", width=20, command=self.controller.quit_callback)
        self.quit_button.pack(pady=10)
