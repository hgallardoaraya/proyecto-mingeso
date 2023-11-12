import type { Metadata } from 'next'
import { montserrat } from './fonts'
import './globals.css'
import { Navbar } from './components/Navbar'

export const metadata: Metadata = {
  title: 'Topeducation',
  description: 'Aplicación para administrar colegiatura del preuniversitario Topeducation',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) 

{
  return (
    <html lang="en">      
      <body className={`${montserrat.className} antialiased`}>
        <Navbar/>
        {children}
      </body>
    </html>
  )
}
