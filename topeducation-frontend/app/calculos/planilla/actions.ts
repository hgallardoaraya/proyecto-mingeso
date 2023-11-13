"use server"
import { CALCULOS_API_URL } from "@/app/globals"
import { revalidateTag } from "next/cache";

export default async function calcularPlanilla(){
  const response = await fetch(`${CALCULOS_API_URL}/planilla`, {
    method: "POST"    
  })

  const rawResponse = await response.json();
  
  revalidateTag('obtenerReporte')

  return rawResponse;
}