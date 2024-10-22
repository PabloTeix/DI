import tkinter as tk
from tkinter import messagebox
import random


# Función para determinar el ganador
def determinar_ganador(jugador1, jugador2):
    if jugador1 == jugador2:
        return "Empate"
    elif (jugador1 == "Piedra" and jugador2 == "Tijeras") or \
            (jugador1 == "Tijeras" and jugador2 == "Papel") or \
            (jugador1 == "Papel" and jugador2 == "Piedra"):
        return "Jugador 1"
    else:
        return "Jugador 2"


# Función para un jugador contra la máquina
def un_jugador():
    ventana_juego = tk.Toplevel(root)
    ventana_juego.title("Un Jugador")

    # Variables para el puntaje
    puntaje_jugador = tk.IntVar(value=0)
    puntaje_maquina = tk.IntVar(value=0)

    def jugar(eleccion_jugador):
        eleccion_maquina = random.choice(["Piedra", "Papel", "Tijeras"])
        ganador = determinar_ganador(eleccion_jugador, eleccion_maquina)
        if ganador == "Jugador 1":
            puntaje_jugador.set(puntaje_jugador.get() + 1)
        elif ganador == "Jugador 2":
            puntaje_maquina.set(puntaje_maquina.get() + 1)

        resultado.set(f"Jugador 1 saca {eleccion_jugador}, Máquina saca {eleccion_maquina}. Resultado: {ganador}")

        # Verificar si alguien ha alcanzado 3 puntos
        if puntaje_jugador.get() == 3:
            messagebox.showinfo("Fin del juego", "¡Jugador 1 ha ganado!")
            ventana_juego.destroy()
        elif puntaje_maquina.get() == 3:
            messagebox.showinfo("Fin del juego", "¡La máquina ha ganado!")
            ventana_juego.destroy()

    resultado = tk.StringVar()
    resultado.set("Elige tu jugada:")

    tk.Label(ventana_juego, textvariable=resultado).pack(pady=10)
    tk.Label(ventana_juego, text="Puntaje Jugador 1:").pack()
    tk.Label(ventana_juego, textvariable=puntaje_jugador).pack()
    tk.Label(ventana_juego, text="Puntaje Máquina:").pack()
    tk.Label(ventana_juego, textvariable=puntaje_maquina).pack()

    tk.Button(ventana_juego, text="Piedra", command=lambda: jugar("Piedra")).pack(side=tk.LEFT, padx=20)
    tk.Button(ventana_juego, text="Papel", command=lambda: jugar("Papel")).pack(side=tk.LEFT, padx=20)
    tk.Button(ventana_juego, text="Tijeras", command=lambda: jugar("Tijeras")).pack(side=tk.LEFT, padx=20)


# Función para dos jugadores
def dos_jugadores():
    ventana_juego = tk.Toplevel(root)
    ventana_juego.title("Dos Jugadores")

    # Variables para el puntaje
    puntaje_jugador1 = tk.IntVar(value=0)
    puntaje_jugador2 = tk.IntVar(value=0)

    def jugar():
        eleccion1 = eleccion_jugador1.get()
        eleccion2 = eleccion_jugador2.get()

        ganador = determinar_ganador(eleccion1, eleccion2)
        if ganador == "Jugador 1":
            puntaje_jugador1.set(puntaje_jugador1.get() + 1)
        elif ganador == "Jugador 2":
            puntaje_jugador2.set(puntaje_jugador2.get() + 1)

        resultado.set(f"Jugador 1 saca {eleccion1}, Jugador 2 saca {eleccion2}. Resultado: {ganador}")

        # Verificar si alguien ha alcanzado 3 puntos
        if puntaje_jugador1.get() == 3:
            messagebox.showinfo("Fin del juego", "¡Jugador 1 ha ganado!")
            ventana_juego.destroy()
        elif puntaje_jugador2.get() == 3:
            messagebox.showinfo("Fin del juego", "¡Jugador 2 ha ganado!")
            ventana_juego.destroy()

    resultado = tk.StringVar()
    resultado.set("Elige tu jugada:")

    eleccion_jugador1 = tk.StringVar()
    eleccion_jugador2 = tk.StringVar()

    tk.Label(ventana_juego, textvariable=resultado).pack(pady=10)
    tk.Label(ventana_juego, text="Puntaje Jugador 1:").pack()
    tk.Label(ventana_juego, textvariable=puntaje_jugador1).pack()
    tk.Label(ventana_juego, text="Puntaje Jugador 2:").pack()
    tk.Label(ventana_juego, textvariable=puntaje_jugador2).pack()

    tk.Label(ventana_juego, text="Jugador 1:").pack()
    tk.Radiobutton(ventana_juego, text="Piedra", variable=eleccion_jugador1, value="Piedra").pack(side=tk.LEFT, padx=20)
    tk.Radiobutton(ventana_juego, text="Papel", variable=eleccion_jugador1, value="Papel").pack(side=tk.LEFT, padx=20)
    tk.Radiobutton(ventana_juego, text="Tijeras", variable=eleccion_jugador1, value="Tijeras").pack(side=tk.LEFT,
                                                                                                    padx=20)

    tk.Label(ventana_juego, text="Jugador 2:").pack()
    tk.Radiobutton(ventana_juego, text="Piedra", variable=eleccion_jugador2, value="Piedra").pack(side=tk.LEFT, padx=20)
    tk.Radiobutton(ventana_juego, text="Papel", variable=eleccion_jugador2, value="Papel").pack(side=tk.LEFT, padx=20)
    tk.Radiobutton(ventana_juego, text="Tijeras", variable=eleccion_jugador2, value="Tijeras").pack(side=tk.LEFT,
                                                                                                    padx=20)

    tk.Button(ventana_juego, text="Jugar", command=jugar).pack(pady=20)


# Función para salir del programa
def salir():
    root.destroy()


# Función principal
if __name__ == "__main__":
    root = tk.Tk()
    root.title("Piedra, Papel, Tijeras")

    tk.Label(root, text="Seleccione el modo de juego:").pack(pady=10)
    tk.Button(root, text="Un Jugador", command=un_jugador).pack(pady=5)
    tk.Button(root, text="Dos Jugadores", command=dos_jugadores).pack(pady=5)
    tk.Button(root, text="Salir", command=salir).pack(pady=5)

    root.mainloop()
