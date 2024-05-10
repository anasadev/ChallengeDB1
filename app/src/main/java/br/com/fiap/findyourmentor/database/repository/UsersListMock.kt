package br.com.fiap.findyourmentor.database.repository

import br.com.fiap.findyourmentor.model.User

fun getAllUsers(): List<User> {
    return listOf(
        User(id = 1, name = "Alan", location = "São Paulo", profileType = "aprendiz", presentation = "Olá, meu nome é Alan e trabalho como dev backend há 1 ano.", interestsList = "Java, MySQL"),
        User(id = 1, name = "Ana", location = "Campinas", profileType = "aprendiz", presentation = "Olá, meu nome é Ana e estudo para ser dev frontend há 6 meses. Quero melhorar meus conhecimentos em JS e CSS para conseguir meu primeiro emprego", interestsList = "JavaScript, CSS"),
        User(id = 1, name = "Chris", location = "São Paulo", profileType = "mentor", presentation = "Olá, meu nome é Chris e trabalho como dev backend há 7 anos. Será um prazer ajudar a melhorar suas habilidade em Java. Também posso te dar dicas pras entrevistas de emprego", interestsList = "PHP, CSS"),
        User(id = 1, name = "Giselle", location = "Florianópolis", profileType = "mentor", presentation = "Olá, meu nome é Giselle e trabalho como dev backend há 7 anos. Será um prazer ajudar a melhorar suas habilidade em Java. Também posso te dar dicas pras entrevistas de emprego", interestsList = "Python, MySQL"),
    )
}