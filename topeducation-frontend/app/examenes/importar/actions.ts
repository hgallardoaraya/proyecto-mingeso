"use server";

import { EXAMENES_API_URL } from "@/app/globals";
import { revalidateTag } from "next/cache";

export default async function importarExamenes(formData: FormData) {
  const response = await fetch(EXAMENES_API_URL, {
    method: "POST",
    body: formData
  });
  const rawResponse = await response.json();

  revalidateTag('obtenerReporte')
  
  return rawResponse;
}