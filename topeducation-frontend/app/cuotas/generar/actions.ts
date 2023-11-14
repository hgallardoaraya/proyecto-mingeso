"use server";

import { revalidateTag } from "next/cache";
import { RAZONES_API_URL } from "../../globals";

export async function generarCuotas( rut:string, numCuotas:string ) {
  const formData = new FormData();
  formData.append('rut', rut);
  formData.append('numCuotas', numCuotas);

  const response = await fetch(RAZONES_API_URL, {
    method: "POST",    
    body: formData
  });

  const rawResponse = await response.json();

  revalidateTag('obtenerRazones' + rut);
  revalidateTag('obtenerReporte');

  return rawResponse;
}