


package org.barberia.servicios;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Envía un correo electrónico.
     *
     * @param destinatario Correo del receptor
     * @param asunto       Asunto del correo
     * @param cuerpo       Contenido del correo (texto plano o HTML)
     * @param esHtml       true si el contenido es HTML, false si es texto plano
     */
    public void enviarCorreo(String destinatario, String asunto, String cuerpo, boolean esHtml) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo, esHtml); // true para HTML

            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar para enviar correos de texto plano sin HTML.
     */
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        enviarCorreo(destinatario, asunto, cuerpo, false);
    }
}
