package com.shuetrainer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.json.JSONArray

data class Fault(val code: Int, val title: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = assets.open("faults.json").bufferedReader().use { it.readText() }
        val array = JSONArray(json)
        val faults = (0 until array.length()).map {
            val obj = array.getJSONObject(it)
            Fault(obj.getInt("code"), obj.getString("title"))
        }
        setContent { MaterialTheme { Trainer(faults) } }
    }
}

@Composable
fun Trainer(faults: List<Fault>) {
    var tab by remember { mutableIntStateOf(0) }
    var command by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf("none") }
    val energized = command && failure != "power" && failure != "coil"
    val contact = energized && failure != "contact"
    Scaffold(topBar = {
        Surface(shadowElevation = 3.dp) {
            Text("ШУЭ-Тренажёр · 0.1.3", Modifier.fillMaxWidth().padding(18.dp),
                style = MaterialTheme.typography.titleLarge)
        }
    }) { padding ->
        Column(Modifier.padding(padding).padding(12.dp)) {
            TabRow(selectedTabIndex = tab) {
                listOf("Шкаф", "Схема", "Отказы").forEachIndexed { index, title ->
                    Tab(selected = tab == index, onClick = { tab = index }, text = { Text(title) })
                }
            }
            Spacer(Modifier.height(16.dp))
            when (tab) {
                0 -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Компоновка ШУЭ (черновой макет)", style = MaterialTheme.typography.titleMedium)
                    listOf("Лицевая панель", "Блоки питания PW", "Реле KA / KT / KS",
                        "Материнская плата", "Контакторы 1JC–4JC", "Клеммники").forEach {
                        Text("▣  " + it)
                    }
                    Text("Фотографическая интерактивная панель будет добавлена в следующих версиях.")
                }
                1 -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("KA1 — условная учебная модель", style = MaterialTheme.typography.titleMedium)
                    Text("Команда: " + if (command) "ВКЛ" else "ВЫКЛ")
                    Text("KA1: " + if (energized) "сработало" else "не сработало")
                    Text("KA1-1: " + if (contact) "замкнут" else "разомкнут")
                    Text("Питание: " + if (failure == "power") "0 В" else "12 В")
                    Text("Катушка: " + if (command && failure != "power") "12 В" else "0 В")
                    Button(onClick = { command = !command }) {
                        Text(if (command) "Снять команду" else "Подать команду")
                    }
                    Text("Преподаватель: задать неисправность")
                    listOf("none" to "Исправно", "power" to "Нет питания",
                        "coil" to "Обрыв катушки", "contact" to "Отказ контакта").forEach { (key, label) ->
                        Row {
                            RadioButton(selected = failure == key, onClick = { failure = key })
                            Text(label, Modifier.padding(top = 12.dp))
                        }
                    }
                    Text("Значения условные, не нормативы реального шкафа.")
                }
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { Text("Коды отказов 1–15", style = MaterialTheme.typography.titleMedium) }
                    items(faults) { Text("${it.code}. ${it.title}") }
                }
            }
        }
    }
}
