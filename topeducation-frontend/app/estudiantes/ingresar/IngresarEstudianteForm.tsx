'use client'

import { useState } from "react"
import { ingresarEstudiante } from "./actions"
import { ResponseEstudiante } from "@/types/Responses"
import IngresarEstudiante from "@/types/IngresarEstudiante"

export default function IngresarEstudianteForm(){
  const [rut, setRut] = useState('')
  const [nombre1, setNombre1] = useState('')
  const [nombre2, setNombre2] = useState('')
  const [apellido1, setApellido1] = useState('')
  const [apellido2, setApellido2] = useState('')
  const [fechaNacimiento, setFechaNacimiento] = useState('')
  const [anioEgreso, setAnioEgreso] = useState('')
  const [nombreColegio, setNombreColegio] = useState('')
  const [idTipoColegio, setIdTipoColegio] = useState('');
  const [idTipoPagoArancel, setIdTipoPagoArancel] = useState('');

  const handleSubmit = async (e:React.MouseEvent<HTMLElement>) => {
    e.preventDefault();
    const anioEgresoInt = parseInt(anioEgreso);
    const idTipoColegioInt = parseInt(idTipoColegio);
    const idTipoPagoArancelInt = parseInt(idTipoPagoArancel);
    const estudiante: IngresarEstudiante = { rut, nombre1, nombre2, apellido1, apellido2, fechaNacimiento, anioEgreso: anioEgresoInt, nombreColegio, idTipoColegio: idTipoColegioInt, idTipoPagoArancel: idTipoPagoArancelInt }
    const response: ResponseEstudiante = await ingresarEstudiante(estudiante);    
    alert(response.message);
  }

  return(
    <>      
      <form className="px-7 flex flex-col items-start gap-5">
        <div className="flex flex-col">
          <label htmlFor="rut">Rut</label>
          <input type="text" placeholder="Ej: 12.345.678-9" id="rut" value={rut} onChange={(e) => setRut(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="nombre1">Primer nombre</label>
          <input type="text" placeholder="Ej: Juan" id="nombre1" value={nombre1} onChange={(e) => setNombre1(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="nombre2">Segundo nombre</label>
          <input type="text" placeholder="Ej: Román" id="nombre2" value={nombre2} onChange={(e) => setNombre2(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="apellido1">Apellido paterno</label>
          <input type="text" placeholder="Ej: Perez" id="apellido1" value={apellido1} onChange={(e) => setApellido1(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="apellido2">Apellido materno</label>
          <input type="text" placeholder="Ej: Diaz" id="apellido2" value={apellido2} onChange={(e) => setApellido2(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="fechaNacimiento">Fecha de nacimiento</label>
          <input type="date" id="fechaNacimiento" value={fechaNacimiento} onChange={(e) => setFechaNacimiento(e.target.value)}/>          
        </div>        
        
        <div className="flex flex-col">
          <label htmlFor="anioEgreso">Año de egreso</label>
          <input type="number" placeholder="Ej: 2020" id="anioEgreso" value={anioEgreso} onChange={(e) => setAnioEgreso(e.target.value)}/>
        </div>        
        
        <div className="flex flex-col">
          <label htmlFor="nombreColegio">Nombre colegio</label>
          <input type="text" placeholder="Ej: Liceo 1" id="nombreColegio" value={nombreColegio} onChange={(e) => setNombreColegio(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="idTipoColegio">Id Tipo Colegio</label>
          <input type="number" placeholder="Ej: 1" id="idTipoColegio" value={idTipoColegio} onChange={(e) => setIdTipoColegio(e.target.value)}/>
        </div>        

        <div className="flex flex-col">
          <label htmlFor="idTipoPagoArancel">Id Tipo Pago Arancel</label>
          <input type="number" placeholder="Ej: 1" id="idTipoPagoArancel" value={idTipoPagoArancel} onChange={(e) => setIdTipoPagoArancel(e.target.value)}/>
        </div>        
        
        <button className="px-3 py-2 text-white bg-blue-600 rounded-md" onClick={handleSubmit}>Ingresar</button>
        
      </form>
    </>
  )
}