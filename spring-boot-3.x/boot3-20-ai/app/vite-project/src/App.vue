<script setup>
import {ref} from "vue";

const msg = ref('')
const result = ref([])
const sendMsg = () => {
  if (msg.value === '') {
    console.log("请输入内容！")
    return;
  }
  let count = 0;
  const eventSource = new EventSource("http://localhost:8080/ai/generateStreamSse?message=" + msg.value);
  if (result.value.length > 0) result.value.push('<br/>')
  result.value.push(msg.value + '<br/>');
  msg.value = '';
  eventSource.onmessage = function (e) {
    if (e.data === '') {
      count++;
    } else {
      count = 0;
    }
    // 连续2个空白，则关闭连接
    if (count === 2) {
      eventSource.close();
    }
    result.value.push(e.data);
  };
};
</script>

<template>
  <div id="container">
    <div id="history">
      <span v-for="(item,idx) in result" :key="idx" v-html="item"></span>
    </div>
    <div id="chat">
      <textarea id="chat-input" placeholder="请输入内容，然后按 Ctrl+Enter 发送" v-model="msg"
                @keydown.ctrl.enter="sendMsg"></textarea>
      <button id="send-btn" @click="sendMsg">Send</button>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

#container {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  width: 100%;
  height: 100vh;
  background-color: white;
  border: 1px solid black;
}

#history {
  background-color: #f9f9f9;
  width: 400px;
  height: 400px;
  overflow-y: auto;
}

#chat {
  background-color: #747bff;
  width: 400px;
  height: 200px;
}

#chat-input {
  box-sizing: border-box;
  width: 400px;
  height: 150px;
  padding: 10px;
}

#send-btn {
  width: 400px;
  height: 50px;
}
</style>
