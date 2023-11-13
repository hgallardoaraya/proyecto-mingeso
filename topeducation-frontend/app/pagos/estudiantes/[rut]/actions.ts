"use server"

import { PAGOS_API_URL } from "@/app/globals"
import { revalidateTag } from "next/cache";
import { cookies } from "next/headers";


export default async function pagarRazones(rut:string, idsRazones:number[]){
  const response = await fetch(PAGOS_API_URL, {
    method: "POST",
    body: JSON.stringify({ rut, idsRazones }),
    headers: {
      "Content-Type": "application/json"
    }    
  })

  const rawResponse = await response.json();

  revalidateTag('obtenerRazones' + rut);
  revalidateTag('obtenerRazonesPendientes' + rut);
  revalidateTag('obtenerReporte')
  cookies().set('exito', 'true');

  return rawResponse;
}