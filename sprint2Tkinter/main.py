from ControladorNotas import ControladorNotas
from NotasModel import NotasModel
from VistaNotas import VistaNotas



class Main:
    def __init__(self):
        self.modelo = NotasModel()
        self.vista = VistaNotas()
        self.controlador = ControladorNotas(self.vista, self.modelo)
        self.vista.mainloop()

if __name__ == "__main__":
    app = Main()
