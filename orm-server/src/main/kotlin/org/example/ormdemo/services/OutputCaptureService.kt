package org.example.ormdemo.services

import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@Service
class OutputCaptureService {

    fun captureOutput(methodToCapture: () -> Unit): String {
        val initialPrintStream = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        methodToCapture()
        System.setOut(initialPrintStream)
        return outputStream.toString()
    }
}