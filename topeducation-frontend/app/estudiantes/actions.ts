"use server";

import { ESTUDIANTES_API_URL } from "../globals";

export async function obtenerEstudiantes() {  
  const response = await fetch(ESTUDIANTES_API_URL, { next: { tags: ['obtenerEstudiantes'] } });

  const rawResponse = await response.json();

  return rawResponse;
}