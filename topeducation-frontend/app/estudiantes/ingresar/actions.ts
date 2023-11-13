"use server";

import { revalidateTag } from "next/cache";
import { ESTUDIANTES_API_URL } from "../../globals";
import IngresarEstudiante from "@/types/IngresarEstudiante";

export async function ingresarEstudiante( estudiante: IngresarEstudiante ) {
  console.log(estudiante);
  const response = await fetch(ESTUDIANTES_API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify( estudiante )
  });

  const rawResponse = await response.json();

  revalidateTag('obtenerEstudiantes')
  revalidateTag('obtenerReporte')

  return rawResponse;
}