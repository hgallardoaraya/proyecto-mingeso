"use server";

import { CALCULOS_API_URL } from "@/app/globals";

export default async function obtenerReporte(){
  const response = await fetch(`${CALCULOS_API_URL}/reporte`, {
    next: { tags: ['obtenerReporte'] }
  })

  const rawResponse = await response.json();
  
  return rawResponse;
}