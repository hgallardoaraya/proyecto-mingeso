"use server";

import { RAZONES_API_URL } from "../globals";

export async function obtenerRazonesPorRut(rut: string ) {  
  const response = await fetch(`${RAZONES_API_URL}?rut=${rut}`, { next: { tags: ['obtenerRazon' + rut] } });

  const rawResponse = await response.json();

  console.log(rawResponse);

  return rawResponse;
}