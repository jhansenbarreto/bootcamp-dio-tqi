package com.credit.creditapp.exception

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.PropertyBindingException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception
import java.time.LocalDateTime
import java.util.stream.Collectors

@ControllerAdvice
class ApiExceptionHandler(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    private val MSG_GENERICA_ERRO = "Erro interno, contate o administrador."

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any>? {

        val erros = ex.bindingResult.allErrors.stream().map {
            var nome = it.objectName
            if (it is FieldError) {
                nome = it.field
            }
            return@map DetalhesErros.OrigemErro(
                name = nome,
                message = messageSource.getMessage(it, LocaleContextHolder.getLocale())
            )
        }.toList()

        val msg = "Um ou mais campos estão inválidos. Corrija e tente novamente."
        return ResponseEntity(montarErro((status as HttpStatus), msg, erros), status)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any>? {
        val msg = String.format("O recurso '%s' que você tentou acessar não existe.", ex.requestURL)
        return ResponseEntity(montarErro((status as HttpStatus), msg, null), status)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest
    ): ResponseEntity<Any>? {

        val causaRaiz = ex.rootCause
        val sts = status as HttpStatus

        if (causaRaiz is InvalidFormatException) {
            return invalidFormatHandle(causaRaiz, sts)
        } else if (causaRaiz is PropertyBindingException) {
            return propertyBindingHandle(causaRaiz, sts)
        }
        val msg = "Corpo da requisição não pôde ser entendido. Verifique a sintaxe."
        return ResponseEntity(montarErro(sts, msg, null), status)
    }

    private fun invalidFormatHandle(ex: InvalidFormatException, status: HttpStatus): ResponseEntity<Any> {
        val msg = String.format(
            "O valor '%s' informado para a propriedade '%s' não é compatível com o tipo '%s'.",
            ex.value,
            joinPath(ex.path),
            ex.targetType.simpleName
        )
        return ResponseEntity(montarErro(status, msg, null), status)
    }

    private fun propertyBindingHandle(ex: PropertyBindingException, status: HttpStatus): ResponseEntity<Any> {
        val msg = String.format(
            "A propriedade '%s' não existe. Corrija e tente novamente.", joinPath(ex.path)
        )
        return ResponseEntity(montarErro(status, msg, null), status)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchHandler(ex: MethodArgumentTypeMismatchException): ResponseEntity<Any>? {
        val status = HttpStatus.BAD_REQUEST
        val msg = String.format(
            "O parâmetro da URL '%s' recebeu o valor '%s' que é incompatível o tipo '%s'.",
            ex.parameter.parameterName,
            ex.value,
            ex.parameter.parameterType.simpleName
        )
        return ResponseEntity(montarErro(status, msg, null), status)
    }

    @ExceptionHandler(DataAccessException::class)
    fun dataAccessHandler(ex: DataAccessException): ResponseEntity<Any>? {
        val status = HttpStatus.CONFLICT
        val msg = ex.mostSpecificCause.message!! //melhorar a mensagem
        return ResponseEntity(montarErro(status, msg, null), status)
    }

    @ExceptionHandler(EntidadeNaoEncontradaException::class)
    fun entidadeNaoEncontradaHandler(ex: EntidadeNaoEncontradaException): ResponseEntity<DetalhesErros> {
        val status = HttpStatus.NOT_FOUND
        return ResponseEntity(montarErro(status, ex.message!!, null), status)
    }

    @ExceptionHandler(RegraNegocioException::class)
    fun regraNegocioHandler(ex: RegraNegocioException): ResponseEntity<DetalhesErros> {
        val status = HttpStatus.BAD_REQUEST
        return ResponseEntity(montarErro(status, ex.message!!, null), status)
    }

    @ExceptionHandler(Exception::class)
    fun genericHandler(ex: Exception): ResponseEntity<DetalhesErros> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity(montarErro(status, MSG_GENERICA_ERRO, null), status)
    }

    private fun montarErro(status: HttpStatus, msg: String, erros: List<DetalhesErros.OrigemErro>?): DetalhesErros =
        DetalhesErros(
            title = status.reasonPhrase,
            timestamp = LocalDateTime.now(),
            status = status.value(),
            message = msg,
            errors = erros
        )

    private fun joinPath(references: List<JsonMappingException.Reference>): String =
        references.stream()
            .map { it.fieldName }
            .collect(Collectors.joining("."))
}