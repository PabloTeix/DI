import tkinter as tk

# Creamos la ventana principal
root = tk.Tk()
root.title("Scrollbar")
root.geometry("300x300")

# Creamos un frame para contener el texto y las barras de desplazamiento
frame = tk.Frame(root)
frame.pack(fill='both', expand=True)

# Creamos el widget Text sin ajuste de palabra (wrap='none')
texto = tk.Text(frame, wrap='none')
texto.grid(row=0, column=0, sticky='nsew')

# Creamos la barra de desplazamiento vertical
scroll_vert = tk.Scrollbar(frame, orient='vertical', command=texto.yview)
scroll_vert.grid(row=0, column=1, sticky='ns')
texto.config(yscrollcommand=scroll_vert.set)

# Creamos la barra de desplazamiento horizontal
scroll_horiz = tk.Scrollbar(frame, orient='horizontal', command=texto.xview)
scroll_horiz.grid(row=1, column=0, sticky='ew')
texto.config(xscrollcommand=scroll_horiz.set)

# Configuramos el frame para expandirse con la ventana
frame.grid_rowconfigure(0, weight=1)
frame.grid_columnconfigure(0, weight=1)

# Insertamos texto largo en el widget Text
texto.insert(tk.END, """Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido. Crea un Text que contenga un texto largo (varios párrafos) y añade una barra de
desplazamiento vertical (Scrollbar) para que el usuario pueda desplazarse a través del
contenido.""")

# Iniciamos el bucle principal de la aplicación
root.mainloop()
