"use client";

import Link from "next/link"
import { usePathname } from "next/navigation"

const links = [
  {
    href: "/",
    label: "Inicio",
  },
  {
    href: "/estudiantes/ingresar",
    label: "Ingresar estudiante",
  },
  {
    href: "/estudiantes",
    label: "Ver estudiantes",
  },
  {
    href: "/cuotas/generar",
    label: "Generar cuotas",
  },
  {
    href: "/cuotas",
    label: "Ver cuotas"
  },
  {
    href: "/pagos/estudiantes",
    label: "Registrar pagos"
  },
  {
    href: "/examenes/importar",
    label: "Importar exámenes"
  },
  {
    href: "/calculos/planilla",
    label: "Calcular planilla"
  },
  {
    href: "/calculos/reporte",
    label: "Reporte resúmen"
  }
]

export function Navbar() {
  const pathname = usePathname();

  return(
    <nav className="px-6 py-3">
      <ul className="flex gap-3">
        {links.map(( link, index ) => 
          <li key={ index }>
            <Link href={ link.href }>
              <span className={`px-3 py-2 rounded-md ${pathname === link.href ? "bg-blue-100 text-blue-600 font-medium" : "text-gray-950"}`}>{ link.label }</span>
            </Link>     
          </li>
        )}
      </ul>
    </nav>
  )
}