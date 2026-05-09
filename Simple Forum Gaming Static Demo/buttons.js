document.addEventListener('DOMContentLoaded', () => {
  const links = document.querySelectorAll('.nav-link');

  links.forEach(link => {
    link.addEventListener('click', function (e) {
      const ripple = document.createElement('span');
      ripple.className = 'ripple';

      const d = Math.max(this.clientWidth, this.clientHeight);
      ripple.style.width  = ripple.style.height = `${d}px`;

      const rect = this.getBoundingClientRect();
      ripple.style.left = `${e.clientX - rect.left - d / 2}px`;
      ripple.style.top  = `${e.clientY - rect.top - d / 2}px`;

      this.appendChild(ripple);
      setTimeout(() => ripple.remove(), 500);
    });
  });
});
