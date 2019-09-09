import { writable } from "svelte/store";

export const messages = writable([
  {
    type: "text",
    text: "Hi, I'm a chat bot!"
  },
  {
    type: "text",
    text: "How can I help you ?"
  },
  {
    type: "quickreply",
    replies: ["Buy beer", "Order food"]
  }
]);
