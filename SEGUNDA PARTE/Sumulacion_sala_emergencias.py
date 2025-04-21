# Importa las librerías necesarias
import simpy  # Para simular procesos de eventos discretos
import random  # Para generar datos aleatorios

# Parámetros iniciales
ramdom_seed = 42  # Semilla para reproducibilidad de los datos aleatorios
intervalo_llegada = 8  # Tiempo promedio entre llegada de pacientes (minutos)
sim_time = 480  # Tiempo total de simulación (minutos, 8 horas)

random.seed(ramdom_seed)  # Fija la semilla para que los resultados sean reproducibles

# Configuración de la cantidad de recursos disponibles
num_enfermeras = 2
num_doctores = 3
num_lab = 2
num_rayosx = 1
num_camillas = 5
num_sillas = 3
num_farmacia = 2

# Lista donde se almacenarán los tiempos totales que cada paciente pasa en emergencias
tiempos_totales = []

# Función que simula la atención de un paciente
def paciente(env, nombre, recursos):
    llegada = env.now  # Hora en la que el paciente llega
    print(f"{nombre} llega a la sala de emergencias a las {llegada:.1f} min")

    severidad = random.randint(1, 5)  # Grado de severidad del paciente (1 = más urgente)

    # Etapa de triage con enfermera
    with recursos['enfermeras'].request(priority=severidad) as req:
        yield req
        yield env.timeout(10)  # Triage tarda 10 minutos

    # Atención por un doctor
    with recursos['doctores'].request(priority=severidad) as req:
        yield req
        yield env.timeout(random.randint(20, 40))  # Atención médica entre 20 a 40 min

    # 50% probabilidad de necesitar laboratorio
    if random.random() < 0.5:
        with recursos['lab'].request(priority=severidad) as req:
            yield req
            yield env.timeout(30)

    # 30% probabilidad de necesitar rayos X
    if random.random() < 0.3:
        with recursos['rayosx'].request(priority=severidad) as req:
            yield req
            yield env.timeout(20)

    # 40% probabilidad de necesitar camilla (observación)
    if random.random() < 0.4:
        with recursos['camillas'].request(priority=severidad) as req:
            yield req
            yield env.timeout(random.randint(30, 60))  # Tiempo variable en camilla

    # 70% probabilidad de usar silla de ruedas
    if random.random() < 0.7:
        with recursos['sillas'].request(priority=severidad) as req:
            yield req
            yield env.timeout(5)

    # 60% probabilidad de recibir medicación en farmacia
    if random.random() < 0.6:
        with recursos['farmacia'].request(priority=severidad) as req:
            yield req
            yield env.timeout(15)

    # Registro del tiempo total del paciente
    salida = env.now
    tiempo_total = salida - llegada
    tiempos_totales.append(tiempo_total)

    print(f"{nombre} sale de la sala de emergencias a las {salida:.1f} min (tiempo total: {tiempo_total:.1f} min)")

# Generador infinito de pacientes
def generador_pacientes(env, recursos):
    i = 0
    while True:
        # Espera un intervalo aleatorio antes de generar el siguiente paciente
        yield env.timeout(random.expovariate(1.0 / intervalo_llegada))
        i += 1
        # Crea el proceso de atención para un nuevo paciente
        env.process(paciente(env, f"Paciente {i}", recursos))

# Función principal que configura y ejecuta la simulación
def main():
    env = simpy.Environment()  # Crea el entorno de simulación

    # Definición de recursos como PriorityResource
    recursos = {
        'enfermeras': simpy.PriorityResource(env, capacity=num_enfermeras),
        'doctores': simpy.PriorityResource(env, capacity=num_doctores),
        'lab': simpy.PriorityResource(env, capacity=num_lab),
        'rayosx': simpy.PriorityResource(env, capacity=num_rayosx),
        'camillas': simpy.PriorityResource(env, capacity=num_camillas),
        'sillas': simpy.PriorityResource(env, capacity=num_sillas),
        'farmacia': simpy.PriorityResource(env, capacity=num_farmacia)
    }

    # Inicia el generador de pacientes
    env.process(generador_pacientes(env, recursos))
    env.run(until=sim_time)  # Ejecuta la simulación durante el tiempo definido

    # Resultados finales
    promedio = sum(tiempos_totales) / len(tiempos_totales)
    print(f'Tiempo promedio en sala de emergencia: {promedio: .2f} min')
    print(f'Total de pacientes atendidos: {len(tiempos_totales)}')

# Ejecuta el programa principal
if __name__ == "__main__":
    main()
    print("Simulación de sala de emergencias")
