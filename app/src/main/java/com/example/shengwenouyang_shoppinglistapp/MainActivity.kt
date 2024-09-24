package com.example.shengwenouyang_shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import com.example.shengwenouyang_shoppinglistapp.ui.theme.ShengwenOuyangShoppingListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShengwenOuyangShoppingListAppTheme {
                // 包装整个UI
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        ShoppingListScreen(modifier = Modifier.padding(innerPadding))
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier) {
    // 创建用于储存购物清单的状态
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    val shoppingList = remember { mutableStateListOf<ShoppingItem>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 输入商品名称和数量的 TextField
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 按钮，用于添加项目到购物清单
        Button(
            onClick = {
                if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                    shoppingList.add(ShoppingItem(itemName, itemQuantity.toInt(), false))
                    itemName = ""
                    itemQuantity = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 使用 LazyColumn 显示购物清单
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(shoppingList) { item ->
                ShoppingItemRow(item = item, onCheckedChange = { checked ->
                    val index = shoppingList.indexOf(item)
                    shoppingList[index] = item.copy(isChecked = checked)
                })
            }
        }
    }
}

// 购物项目的数据类
data class ShoppingItem(val name: String, val quantity: Int, val isChecked: Boolean)

// 显示每个购物项目的行
@Composable
fun ShoppingItemRow(item: ShoppingItem, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Quantity: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
        }
        Checkbox(checked = item.isChecked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    ShengwenOuyangShoppingListAppTheme {
        ShoppingListScreen()
    }
}
