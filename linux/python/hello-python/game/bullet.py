import pygame
from pygame.sprite import Sprite


class Bullet(Sprite):
    def __init__(self, setting, screen, ship):
        super().__init__()
        self.screen = screen

        self.rect = pygame.Rect(0, 0, setting.bullet_width, setting.bullet_height)
        self.rect.centerx = ship.rect.centerx
        self.rect.top = ship.rect.top

        self.y = float(self.rect.y)
        self.color = setting.bullet_color
        self.speed_factor = setting.bullet_speed_factor
