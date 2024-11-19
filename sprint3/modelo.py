import random
import threading

from recursos import Recursos


class GameModel:
    def __init__(self):
        self.board = []
        self.image_objects = []  # Almacenará las imágenes cargadas
        self.cards_flipped = []  # Estado de las cartas (dada vuelta o no)
        self.recursos = Recursos()  # Instancia de la clase Recursos para manejar las imágenes
        self.images_loaded = False

    def _generate_board(self, difficulty):
        """Genera el tablero de juego dependiendo de la dificultad."""
        num_cards = 6 if difficulty == "facil" else 12 if difficulty == "medio" else 18
        self.board = random.sample(range(1, 10), num_cards) * 2  # 2 veces cada número de carta
        random.shuffle(self.board)

    def _load_images(self, image_urls, callback):
        """Descargar las imágenes de las cartas y la parte posterior de la carta."""
        # Cargar la imagen de la carta dada vuelta
        self.image_objects = [self.recursos.cargar_imagen_comun()]

        # Cargar las imágenes de las cartas
        for url in image_urls:
            image = self.recursos.cargar_imagen(url)  # Cargar cada imagen de carta
            if image:
                self.image_objects.append(image)

        # Estado de las cartas (dada vuelta o no)
        self.cards_flipped = [False] * len(self.board)  # Todas las cartas están dadas vuelta inicialmente
        self.images_loaded = True
        callback()  # Llamar al callback para actualizar la interfaz

    def load_images_thread(self, image_urls, callback):
        """Carga las imágenes en un hilo para no bloquear la interfaz."""
        threading.Thread(target=self._load_images, args=(image_urls, callback), daemon=True).start()

    def flip_card(self, card_index):
        """Voltear una carta y mostrar su imagen correspondiente."""
        if not self.cards_flipped[card_index]:
            self.cards_flipped[card_index] = True
            return self.image_objects[self.board[card_index] - 1]  # Devolver la imagen de la carta volteada
        return None

    def get_card_back(self):
        """Devolver la imagen de la carta dada vuelta (la carta común)."""
        return self.image_objects[0]  # La primera imagen en la lista es la parte posterior de la carta
