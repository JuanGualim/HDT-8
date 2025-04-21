import simpy
import random

ramdom_seed = 42
intervalo_llegada = 8
sim_time = 480

random.seed(ramdom_seed)

num_enfermeras = 2
num_doctores = 3
num_lab = 2
num_rayosx = 1
num_camillas = 5
num_sillas = 3
num_farmacia = 2

tiempos_totales = []

def paciente(env, nombre, recursos):
    llegada = env.now
    print(f"{nombre} llega a la sala de emergencias a las {llegada:.1f} min")

    severidad = random.randint(1, 5)

    with recursos['enfermeras'].request(priority = severidad) as req:
        yield req
        yield env.timeout(10)

    with recursos['doctores'].request(priority = severidad) as req:
        yield req
        yield env.timeout(random.randint(20,40))

    if random.random() < 0.5:
        with recursos['lab'].request(priority = severidad) as req:
            yield req
            yield env.timeout(30)

    if random.random() < 0.3:
        with recursos['rayosx'].request(priority = severidad) as req:
            yield req
            yield env.timeout(20)
    
    if random.random() < 0.4:
        with recursos['camillas'].request(priority = severidad) as req:
            yield req
            yield env.timeout(random.randint(30, 60))

    if random.random() < 0.7:
        with recursos['sillas'].request(priority = severidad) as req:
            yield req
            yield env.timeout(5)

    if random.random() < 0.6:
        with recursos['farmacia'].request(priority = severidad) as req:
            yield req
            yield env.timeout(15)

    salida = env.now
    tiempo_total = salida - llegada
    tiempos_totales.append(tiempo_total)
    print(f"Paciente {nombre} sale de la sala de emergencias a las {salida:.1f} min (tiempo total: {tiempo_total:.1f} min)")

def generador_pacientes(env, recursos):
    i = 0
    while True:
        yield env.timeout(random.expovariate(1.0 / intervalo_llegada))
        i += 1
        env.process(paciente(env, f"Paciente {i}", recursos))

def main():
    env = simpy.Environment()

    recursos = {
        'enfermeras': simpy.PriorityResource(env, capacity=num_enfermeras),
        'doctores': simpy.PriorityResource(env, capacity=num_doctores),
        'lab': simpy.PriorityResource(env, capacity=num_lab),
        'rayosx': simpy.PriorityResource(env, capacity=num_rayosx),
        'camillas': simpy.PriorityResource(env, capacity=num_camillas),
        'sillas': simpy.PriorityResource(env, capacity=num_sillas),
        'farmacia': simpy.PriorityResource(env, capacity=num_farmacia)
    }

    env.process(generador_pacientes(env, recursos))
    env.run(until=sim_time)

    promedio = sum(tiempos_totales) / len(tiempos_totales)
    print(f'Tiempo promedio en sale de emergencia: {promedio: .2f} min')
    print(f'Total de pacientes atendidos: {len(tiempos_totales)}')

if __name__ == "__main__":
    main()
    print("Simulación de sala de emergencias")