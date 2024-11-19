import os
import requests
from PIL import Image, ImageTk


class Recursos:
    def __init__(self, folder="images"):
        self.folder = folder
        if not os.path.exists(folder):
            os.makedirs(folder)

    def descargar_imagen(self, url, filename):
        """Descargar la imagen desde la URL y guardarla en el archivo local usando requests."""
        try:
            print(f"Descargando {url}...")
            response = requests.get(url)
            response.raise_for_status()  # Verifica si hubo un error en la solicitud HTTP
            with open(filename, 'wb') as f:
                f.write(response.content)  # Escribir el contenido de la imagen en un archivo
            print(f"Imagen guardada en {filename}")
            return filename  # Devolver el nombre del archivo descargado
        except requests.exceptions.RequestException as e:
            print(f"Error al descargar la imagen: {e}")
            return None

    def cargar_imagen(self, image_url):
        """Cargar la imagen en formato compatible con Tkinter desde una URL."""
        try:
            # Descargar la imagen
            filename = os.path.join(self.folder, image_url.split("/")[-1])
            if not os.path.exists(filename):
                self.descargar_imagen(image_url, filename)

            # Cargar la imagen con PIL y redimensionarla
            img = Image.open(filename)
            img = img.resize((100, 100))  # Redimensionar la imagen
            return ImageTk.PhotoImage(img)
        except Exception as e:
            print(f"Error al cargar la imagen {image_url}: {e}")
            return None

    def cargar_imagen_comun(self,
                            image_url="https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/hidden.jpg"):
        """Cargar la imagen común de la carta dada vuelta (por ejemplo, la parte posterior de la carta)."""
        # Asegurarse de que estamos usando una URL completa, no una ruta local
        if not image_url.startswith('http'):
            print("La URL no es válida, usando URL predeterminada para la parte posterior de la carta.")
            image_url = "https://raw.githubusercontent.com/PabloTeix/DI/refs/heads/main/hidden.jpg"

        filename = os.path.join(self.folder, "back.png")
        if not os.path.exists(filename):
            self.descargar_imagen(image_url, filename)
        return self.cargar_imagen(filename)
