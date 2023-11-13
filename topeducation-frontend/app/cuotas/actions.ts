"use server";

import { RAZONES_API_URL } from "../globals";

export async function obtenerRazonesPorRut(rut: string ) {  
  const response = await fetch(`${RAZONES_API_URL}/estudiantes/${rut}`, { next: { tags: ['obtenerRazones' + rut] } });

  const rawResponse = await response.json();

  console.log(rawResponse);

  return rawResponse;
}


export async function obtenerRazonesPendientesPorRut(rut: string ) {  
  const response = await fetch(`${RAZONES_API_URL}/estudiantes/${rut}?estados=2,3`, { next: { tags: ['obtenerRazonesPendientes' + rut] } });

  const rawResponse = await response.json();  

  return rawResponse;
}